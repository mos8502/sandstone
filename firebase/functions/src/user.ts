import { OauthClientFactory } from "./ouath";
import * as firebase from 'firebase-admin'
import { TokenPayload } from "google-auth-library/build/src/auth/loginticket";
import { VerifyIdTokenOptions } from "google-auth-library";

export interface UserModel {
    signIn(authCode: string): Promise<String>
}

class DefaultUserModel implements UserModel {
    private oauthClientFactory: OauthClientFactory;
    private firebaseAuth: firebase.auth.Auth;
    private firestore: firebase.firestore.Firestore;

    constructor(
        oauthClientFactory: OauthClientFactory,
        firebaseAuth: firebase.auth.Auth,
        firestore: firebase.firestore.Firestore) {
        this.oauthClientFactory = oauthClientFactory;
        this.firebaseAuth = firebaseAuth;
        this.firestore = firestore;
    }

    async signIn(authCode: string): Promise<string> {
        const oauthClient = await this.oauthClientFactory.newInstance();
        const tokenResponse = await oauthClient.getToken(authCode);
        const idToken = tokenResponse.tokens.id_token;
        if (idToken) {
            const options: VerifyIdTokenOptions = { idToken: idToken, audience: oauthClient._clientId as string}
            const idTokenDetails = await oauthClient.verifyIdToken(options);
            const payload = idTokenDetails.getPayload();
            if (payload) {
                try {
                    await this.firebaseAuth.getUser(payload.sub);
                } catch (error) {
                    await this.firestore.collection('user_credentials')
                        .doc(payload.sub)
                        .set(tokenResponse.tokens);
                    const importResult = await this.firebaseAuth.importUsers([this.toUserImportRecord(payload)]);
                    if (importResult.failureCount > 0) {
                        throw new Error('user import failed');
                    }
                }
                return idToken;
            } else {
                throw new Error('invalid id token');
            }
        } else {
            throw new Error('id token not found');
        }
    }

    private toUserImportRecord(payload: TokenPayload): firebase.auth.UserImportRecord {
        return {
            uid: payload.sub,
            displayName: payload.name,
            email: payload.email,
            photoURL: payload.picture,
            emailVerified: payload.email_verified === true,
            providerData: [this.toUserInfo(payload)]
        } as firebase.auth.UserImportRecord
    }

    private toUserInfo(payload: TokenPayload): firebase.auth.UserInfo {
        return {
            uid: payload.sub,
            displayName: payload.name,
            email: payload.email,
            photoURL: payload.picture,
            providerId: 'google.com'
        } as firebase.auth.UserInfo
    }
}

export function newUserModel(oauthClientFactory: OauthClientFactory,
    firebaseAuth: firebase.auth.Auth,
    firestore: firebase.firestore.Firestore): UserModel {
    return new DefaultUserModel(oauthClientFactory, firebaseAuth, firestore);
}