import * as firebase from 'firebase-admin'
import { Request, Response } from 'express';
import { ApolloServer, AuthenticationError, gql } from 'apollo-server-express';

import { OauthClientFactory, newOauthClientFactory } from './ouath';
import { UserModel, newUserModel } from './user';
import { newTasksModel, TasksModel, TaskList } from './tasks'
import { Credentials } from 'google-auth-library';
import { ExpressContext } from 'apollo-server-express/dist/ApolloServer';

const functions = require('firebase-functions');

const express = require('express');
const login = express();
const graphql = express();
const app = firebase.initializeApp()

const schema = gql`
    type Query {
        taskLists(maxResults: Int, pageToken: String): TaskLists!
        taskList(id: String!): TaskList!
    }

    type TaskLists {
        etag: String!
        nextPageToken: String
        items: [TaskList!]!
    }

    type TaskList {
        id: String!
        etag: String
        title: String!
        updated: String!
        tasks: [Task!]!
    }

    type Task {
        id: String!
        etag: String!
        title: String!
        updated: String!
        notes: String
        status: String!
        due: String
        completed: String
    }
`;

interface TaskListQueryArgs {
    maxResults: number;
    pageToken: string;
}

interface TaskQueryArgs {
    id: string;
}

class TasksContext {
    readonly tasks: TasksModel

    constructor(tasks: TasksModel) {
        this.tasks = tasks;
    }
}

const oauthClientFactory: OauthClientFactory = newOauthClientFactory(app.firestore());
const userModel: UserModel = newUserModel(oauthClientFactory, app.auth(), app.firestore());

const resolvers = {
    Query: {
        taskLists: async (_: any, queryArgs: TaskListQueryArgs, context: TasksContext) => {
            return await context.tasks.taskLists(queryArgs.pageToken, queryArgs.maxResults);
        },
        taskList: async (_: any, args: TaskQueryArgs, context: TasksContext) =>  {
            return await context.tasks.taskList(args.id);
        }
    },

    TaskList: {
        tasks: async (taskList :TaskList, args: any, context: TasksContext) =>  {
            return await context.tasks.tasks(taskList.id);
        }
    }
};

login.get('/', async (request: Request, response: Response) => {
    request.query
    if (request.query.authCode) {
        try {
            response.status(200).send(await userModel.signIn(request.query.authCode));
        } catch (error) {
            response.status(401).send(error);
        }
    } else {
        response.status(400).send("authCode required");
    }
})

const apolloServer = new ApolloServer({
    typeDefs: schema,
    resolvers: resolvers,
    introspection: true,
    playground: true,
    formatError: error => {
        console.log(error);
        return error
    },
    context: async (expressContext: ExpressContext) => {
        const isIntrospectionQuery = expressContext.connection && 
            expressContext.connection.operationName === "IntrospectionQuery" ||
            expressContext.req.body.operationName === "IntrospectionQuery";

        if (isIntrospectionQuery) {
            return {};
        } else {
            if (expressContext.req.headers.authorization && expressContext.req.headers.authorization.startsWith('Bearer ')) {
                try {
                    const token = await app.auth().verifyIdToken(expressContext.req.headers.authorization.split('Bearer ')[1])
                    const uid = token.uid
                    const oauthClient = await oauthClientFactory.newInstance();
                    const doc = await app.firestore().collection('user_credentials').doc(uid).get();
                    const data = doc.data();

                    if (!data) {
                        throw new AuthenticationError('user credentials not found');
                    }

                    const credentials: Credentials = {
                        refresh_token: data["refresh_token"],
                        expiry_date: data["expiry_date"],
                        access_token: data["access_token"],
                        token_type: data["token_type"],
                        id_token: data["id_token"]
                    };
                    oauthClient.setCredentials(credentials);
                    return {
                        tasks: newTasksModel(oauthClient)
                    }
                } catch (error) {
                    throw new AuthenticationError("token verification failed");
                }
            } else {
                throw new AuthenticationError('unauthorized');
            }
        }
    }
});

apolloServer.applyMiddleware({ app: graphql, path: '/' });

exports.api = functions.https.onRequest(graphql);
exports.login = functions.https.onRequest(login);
