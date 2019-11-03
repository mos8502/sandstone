import { google, tasks_v1 } from 'googleapis';
import { OAuth2Client } from "google-auth-library";
import { GaxiosResponse } from 'gaxios';

export interface TaskLists {
    etag?: string | null
    nextPageToken?: string | null;
    items: TaskList[];
}

export interface TaskList {
    id: string;
    etag?: string | null;
    title: string;
    updated: string;
    tasks: Task[]
}

export interface Task {
    id: string;
    etag: string;
    title: string;
    updated: string;
    notes: string | null;
    status: string;
    due: string | null;
    completed: string | null;
}

export interface TasksModel {
    taskLists(pageToken?: string, maxResults?: number): Promise<TaskLists>;

    taskList(id: String): Promise<TaskList>

    tasks(taskListId: string, pageToken?: string, maxResults?: string): Promise<Task[]>
}

export function newTasksModel(oauthClient: OAuth2Client): TasksModel {
    const options: tasks_v1.Options = {
        version: 'v1',
        auth: oauthClient
    }
    return new DefaultTasksModel(google.tasks(options));
}

class DefaultTasksModel implements TasksModel {
    private googleTasks: tasks_v1.Tasks;

    constructor(googleTasks: tasks_v1.Tasks) {
        this.googleTasks = googleTasks;
    }

    async taskLists(pageToken?: string, maxResults?: number): Promise<TaskLists> {

        const params: tasks_v1.Params$Resource$Tasklists$List = {}
        if (pageToken) {
            params.pageToken = pageToken;
        }

        if (maxResults) {
            params.maxResults = maxResults.toString()
        }

        const result = await this.googleTasks.tasklists.list(params);
        
        return result.data as TaskLists;
    }

    async taskList(id: string): Promise<TaskList> {
        const params: tasks_v1.Params$Resource$Tasklists$Get = {tasklist: id};
        const result = await this.googleTasks.tasklists.get(params);
        return result.data as TaskList;
    }

    async tasks(taskListId: string): Promise<Task[]> {
        const params: tasks_v1.Params$Resource$Tasks$List = { tasklist: taskListId }

        let tasks: Task[] = [];
        let result: GaxiosResponse<tasks_v1.Schema$Tasks> | null = null;
        
        do {
            result = await this.googleTasks.tasks.list(params);

            if (result.data) {
                tasks = tasks.concat(result.data.items as Task[]);
            }

            if (result.data.nextPageToken) {
                params.pageToken = result.data.nextPageToken
            }
        } while (result.data.nextPageToken)

        return tasks;
    }
}