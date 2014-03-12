Scenario: 0001 設定ファイルからアプリケーションをダウンロード

Given 初期化
When テンポラリディレクトリの作成
When Bootファイル読み込み stories/boot0001.xml
Then 更新用URLが http://tamurashingo.github.io/jalo/integration/data/boot0001 であること
Then 自動更新が 1 であること
When アプリケーションディレクトリの削除
When アプリケーション読み込み
Then アプリケーションのバージョンが 0.1 であること


Scenario: 0002 アプリケーションのアップデート
!-- 0001でダウンロードしたアプリケーションを引き続き使う

Given 初期化
When Bootファイル読み込み stories/boot0002.xml
When アプリケーション読み込み
Then 更新用URLが http://tamurashingo.github.io/jalo/integration/data/boot0002 であること
Then 自動更新が 1 であること
When アプリケーション読み込み
Then アプリケーションのバージョンが 0.2 であること


Scenario: 0003 アプリケーションを自動更新しない
!-- 0002で使用したアプリケーションを引き続き使う

When Bootファイル読み込み stories/boot0003.xml
Then 更新用URLが http://tamurashingo.github.io/jalo/integration/data/boot0003 であること
Then 自動更新が 0 であること
!-- アプリケーションを更新するかどうかの判断はMainで行っているため、結合試験ではここまで
 

Scenario: 0004 アプリケーションの更新をしない
!-- 0002で使用したアプリケーションを引き続き使う
!-- 0.2(ローカル) > 0.04(サーバ) なので更新しない

When Bootファイル読み込み stories/boot0004.xml
Then 更新用URLが http://tamurashingo.github.io/jalo/integration/data/boot0004 であること
Then 自動更新が 1 であること
When アプリケーション読み込み
Then アプリケーションのバージョンが 0.2 であること
Then tmpディレクトリのapp.xmlのバージョンが 0.04 であること
