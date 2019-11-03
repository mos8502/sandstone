import { OAuth2Client, OAuth2ClientOptions } from "google-auth-library";
import { firestore } from 'firebase-admin';

export interface OauthClientFactory {
    newInstance(): Promise<OAuth2Client>;
}

class DefaultOauthClientFactory implements OauthClientFactory {
    private firebaseFirestore: firestore.Firestore;

    constructor(firebaseFirestore: firestore.Firestore) {
        this.firebaseFirestore = firebaseFirestore;
    }

    async newInstance(): Promise<OAuth2Client> {
        const doc = await this.firebaseFirestore.collection('server_config').doc('google_oauth').get();
        return new OAuth2Client(doc.data() as OAuth2ClientOptions);
    }
}

export function newOauthClientFactory(firebaseFirestore: firestore.Firestore): OauthClientFactory {
    return new DefaultOauthClientFactory(firebaseFirestore);
}