import json

import firebase_admin
from firebase_admin import firestore

firebase_admin.initialize_app()

db = firestore.client()

if __name__ == '__main__':
    # Load JSON data
    with open(
            '/Users/mazlum/my-projects/blogarticles/world-cup-qatar-event-driven-serverless-archi/file/world_cup_team_players_stats_raw.json'
    ) as f:
        data = json.load(f)

    collection_name = 'world_cup_team_player_stats_raw'

    for index, item in enumerate(data):
        doc_ref = db.collection(collection_name).document()

        doc_ref.set(item)
        print(f'Uploaded document {index + 1}')

    print('Data upload completed.')
