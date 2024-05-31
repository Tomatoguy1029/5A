import sqlite3
import json
from datetime import datetime
import uuid

# データベースに接続（存在しない場合は作成されます）
conn = sqlite3.connect('akikatu.db')
cursor = conn.cursor()

# JSONファイルからデータを読み込む
with open('Classrooms.json', 'r', encoding='utf-8') as f:
    classroom_data_list = json.load(f)

# データを挿入
for classroom_data in classroom_data_list:
    classroom_data['classroom_id'] = str(uuid.uuid4())  # UUIDを生成して設定
    classroom_data['created_at'] = datetime.now().strftime("%Y-%m-%d %H:%M:%S")
    classroom_data['updated_at'] = datetime.now().strftime("%Y-%m-%d %H:%M:%S")
    classroom_data['available'] = json.dumps(classroom_data['available'], ensure_ascii=False)
    
    # nameフィールドが数値であれば文字列に変換
    if isinstance(classroom_data['name'], int):
        classroom_data['name'] = str(classroom_data['name'])
        
    try:
        cursor.execute('''
        INSERT INTO classrooms (classroom_id, name, location, building, seats, outlets, desk_size, available, created_at, updated_at)
        VALUES (:classroom_id, :name, :location, :building, :seats, :outlets, :desk_size, :available, :created_at, :updated_at)
        ''', classroom_data)
    except sqlite3.IntegrityError as e:
        print(f"Error inserting data: {e}")
        #print(f"Data: {classroom_data}")

# 変更を保存
conn.commit()

# データベース接続を閉じる
conn.close()
print("Data has been inserted successfully.")
