# 変更点
・検索画面から投稿画面へ投稿ボタンで飛べるようにした。
・検索画面を表示する処理と、検索をする処理を、あらかじめ分けてくれていたが、データを取得する
操作などもSearch.javaのほうへ移した。
・クラスのデータが取得するgetSampleData()メソッドを別のファイルへ分割した。

# 実行方法
javac src/client/ClassroomSearchPage.java src/client/Search.java 
javac src/client/ClassroomPostPage.java src/client/SampleData.java
java src/client/ClassroomSearch