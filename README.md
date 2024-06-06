# 5A
software production
情報共有はラインで

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