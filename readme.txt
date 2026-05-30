# ReHyogoMst

## 概要

これは職業訓練校「Java + Pythonプログラマー養成コース」のチーム課題で制作した、Java製の観光Webアプリケーションです。
Servlet / JSP を用いたMVC構成で開発しました。
SQLフォルダ内のインポート用SQLを実行してデータベースを作成してから起動してください。

---

## 開発環境

| 項目            | バージョン                       |
| ------------- | --------------------------- |
| OS            | Windows 11 Pro              |
| Eclipse       | Pleiades All in One 2025-09 |
| Java          | Java SE 21                  |
| Apache Tomcat | 10.1.46                     |
| MySQL         | 9.3.0 Community             |
| JSTL          | 3.0.1                       |
| JDBC Driver   | mysql-connector-j-9.5.0     |

### Eclipse設定情報

このプロジェクトは Eclipse Dynamic Web Project 構成で作成しています。

* Java : 21
* Tomcat : 10
* Servlet Version : 6.0
* Dynamic Web Project

---

## 実行環境

| 項目            | バージョン           |
| ------------- | --------------- |
| OS            | Windows 11 Pro  |
| Java          | Java SE 21      |
| Apache Tomcat | 10.1.46         |
| MySQL         | 9.3.0 Community |

### WARファイル配備環境

* Apache Tomcat 10.1.41

---

## 使用技術

* Java
* Servlet / JSP
* HTML
* CSS
* JavaScript
* MySQL
* JDBC
* Git
* GitHub

---

## 使用ライブラリ

* mysql-connector-j-9.5.0
* jackson-core-2.21.2
* jackson-databind-2.21.2
* jackson-annotations-2.21
* commons-codec-1.21.0
* jakarta.servlet.jsp.jstl-3.0.1
* jakarta.servlet.jsp.jstl-api-3.0.0

---

## ディレクトリ構成

```text
src/main/java
 ├ dao
 ├ model
 └ servlet

src/main/webapp
 ├ WEB-INF
 ├ css
 ├ js
 └ img
```

---

## セットアップ手順

1. SQLフォルダ内のSQLファイルをMySQLへインポート
2. データベース接続情報を環境に合わせて設定
3. Apache Tomcatへプロジェクトをデプロイ
4. サーバーを起動してアクセス
