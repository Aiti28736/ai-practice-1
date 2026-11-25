# Backup Manifest

- 作業日時: 2026-11-21
- 対象練習番号: 2
- スナップショット: `before/`
- 目的: 既存AndroidプロジェクトのActivityとGradle設定を生成前に保全する。

## 保存したパス
- `before/ai-practice-sorcecode-2/app/src/main/java/com/example/aipractice2/MainActivity.kt`
- `before/ai-practice-sorcecode-2/app/build.gradle.kts`
- `before/ai-practice-sorcecode-2/gradle/libs.versions.toml`

## 生成後スナップショット

生成と検証が完了した後、`generated/` に今回の仕様書、ソースコード、差分メモを保存する。

## 補足

- `ai-practice-spec-2/` と機能パッケージは空で、既存仕様・機能ソースはなかった。
- `diff-2/` と質問案ディレクトリも空だった。
- 答え合わせ情報を含む `output/history-2/` はバックアップへ複製しない。

## generated スナップショット

- 作成日時: 2026-11-21（生成・コンパイル検証後）
- 目的: 今回作成した練習2の仕様書、Androidプロジェクト、差分メモを保全する。
- 保存先: `generated/`
- `generated/ai-practice-spec-2/expense-entry-spec.md`
- `generated/ai-practice-spec-2/monthly-summary-spec.md`
- `generated/ai-practice-sorcecode-2/`（Gradleキャッシュ・IDEキャッシュ・build出力を除くプロジェクトファイル51件）
- `generated/diff-2/仕様と実装の差分メモ.md`
