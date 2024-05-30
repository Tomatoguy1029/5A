import sqlite3
import json
from datetime import datetime

# データベースに接続（存在しない場合は作成されます）
conn = sqlite3.connect('akikatu.db')
cursor = conn.cursor()

# 挿入するデータ
classroom_data = {
    "classroom_id": 2,
    "name": "PCルームB",
    "location": "3階",
    "building": 63,
    "seats": 50,
    "outlets": 1, #多い場合は1、少ない場合は0
    "desk_size": 1,  # 広い場合は1、狭い場合は0
    "available": json.dumps({"月曜": [1, 2], "火曜": [3, 4], "水曜": [4], "木曜": [1,2], "金曜": [1, 2, 3, 4], "土曜": [1, 2, 3, 4]}),
    "created_at": datetime.now().strftime("%Y-%m-%d %H:%M:%S"),
    "updated_at": datetime.now().strftime("%Y-%m-%d %H:%M:%S")
}

# データを挿入
cursor.execute('''
INSERT INTO classrooms (classroom_id, name, location, building, seats, outlets, desk_size, available, created_at, updated_at)
VALUES (:classroom_id, :name, :location, :building, :seats, :outlets, :desk_size, :available, :created_at, :updated_at)
''', classroom_data)

# 変更を保存
conn.commit()

# データベース接続を閉じる
conn.close()
