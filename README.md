# 変更点
・検索画面から投稿画面へ投稿ボタンで飛べるようにした。
・検索画面を表示する処理と、検索をする処理を、あらかじめ分けてくれていたが、データを取得する
操作などもSearch.javaのほうへ移した。
・クラスのデータが取得するgetSampleData()メソッドを別のファイルへ分割した。

<<<<<<< HEAD
# 実行方法
javac src/client/ClassroomSearchPage.java src/client/Search.java 
javac src/client/ClassroomPostPage.java src/client/SampleData.java
java src/client/ClassroomSearch
=======
伝説の始まり

# 実行方法
Windowの場合  
コンパイル  
javac -cp ".;lib/*" src/server/Main.java  
javac -cp ".;lib/*" src/client/Main.java  
起動  
java -cp ".;lib/*" src/server/Main  
java -cp ".;lib/*" src/client/Main  

Macの場合  
コンパイル  
javac -cp ".:lib/*" src/**/*.java  
サーバーを起動  
java -cp ".:lib/*" src.server.Main  
クライアントからサーバに接続  
java -cp ".:lib/*" src.client.Main  
>>>>>>> main
