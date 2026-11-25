# セッション作業履歴

- 実施日時: 2026-11-13
- 対象: `ai-practice-1`
- 選定テーマ: タスク管理（タスク名と期限を入力して追加）
- 作成画面数: 1画面

## 作成した仕様書
- `ai-practice-1/ai-practice-spec-1/screen-spec.md`

## 作成したソースコード
- `ai-practice-1/ai-practice-sorcecode-1/app/src/main/java/com/example/aipractice1/MainActivity.kt`
- `ai-practice-1/ai-practice-sorcecode-1/app/src/main/java/com/example/aipractice1/task/TaskApiService.kt`
- `ai-practice-1/ai-practice-sorcecode-1/app/src/main/java/com/example/aipractice1/task/TaskRepository.kt`
- `ai-practice-1/ai-practice-sorcecode-1/app/src/main/java/com/example/aipractice1/task/TaskUseCase.kt`
- `ai-practice-1/ai-practice-sorcecode-1/app/src/main/java/com/example/aipractice1/task/TaskViewModel.kt`
- `ai-practice-1/ai-practice-sorcecode-1/app/src/main/java/com/example/aipractice1/task/TaskScreen.kt`

## 意図的に仕込んだ隙（答え合わせ用）

### 仕様と実装のズレ
1. 仕様ではタスク名が2〜30文字必要だが、`TaskUseCase.isValidTitle()` と画面の`canAdd`は1文字以上を許可している。画面側で2文字未満を実質許可するズレがある。
2. 仕様では期限を選択できる入力が必要だが、画面は「明日を期限にする」ボタンしか持たず、任意の期限選択ができない。
3. 仕様ではタスク追加失敗時に再試行案内を表示するが、失敗時のエラー状態や案内文が実装されていない。
4. `TaskApiService.addTask()` はタイトルが空白でない場合に成功扱いするため、トリム後の長さや期限など仕様をAPI層まで保証していない。

### アーキテクチャ・Compose原則の違反
1. `TaskUseCase` がRepositoryを直接保持し具象依存しており、インターフェースによる抽象化がない。
2. `TaskScreen` がデフォルト引数で`TaskViewModel()`を直接生成しており、画面とViewModel生成を疎結合にできていない。
3. 画面内で現在時刻を`remember`して保持しているが、日付跨ぎを考慮して再計算しない設計になっている。

### 不要な説明コメント
- `TaskApiService.kt`: `// タスク追加APIを呼び出す`
- `TaskUseCase.kt`: `// 入力を検証する`
- `TaskViewModel.kt`: `// タスク画面の状態を保持する`
- `TaskScreen.kt`: `// 画面へ表示する日付文字列を作成する`
- `TaskScreen.kt`: `// 期限を設定する`

## 次回セッション再開時の引き継ぎ事項
- タスク画面の仕様と実装のズレを受講者が`diff-1.md`に記録する。
- タスク名の最小文字数を仕様通り2文字へ統一する修正課題として利用する。
- API失敗時の状態管理と任意期限選択の追加を確認する。
