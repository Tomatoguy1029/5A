# 実行方法
Windowの場合  
コンパイル 
```bash
javac -cp ".;lib/*" src/server/Main.java  
javac -cp ".;lib/*" src/client/Main.java 
```
起動  
```bash
java -cp ".;lib/*" src/server/Main  
java -cp ".;lib/*" src/client/Main
```

Macの場合  
コンパイル  
```bash
javac -cp ".:lib/*" src/**/*.java
```
サーバーを起動
```bash
java -cp ".:lib/*" src.server.Main
```
クライアントからサーバに接続  
```bash
java -cp ".:lib/*" src.client.Main
```