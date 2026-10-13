# AI_USAGE

## Какие AI-инструменты использовал и для чего
Использовал Claude (claude.ai):
- нарисовал эскиз трёх экранов с подписанными компонентами (Scaffold, TopAppBar, LazyRow, LazyColumn, Card, Row, Column);
- предложил структуру проекта и тему приложения («Алматы: места»);
- написал код: цветовую схему, `Spacing`, `Place` и список данных, компоненты (`ItemCard`, `CategoryChip`, `EmptyState`, `PlaceTopBar`), три экрана и навигацию;
- помогал пошагово: команды git, создание пакетов, разбор ошибок по скриншотам.

## 3 самых полезных промпта
1. «вот это задание мне нужно выполнить помоги мне» (с PDF задания и фото примера эскиза)
2. «эскиз сам нарисуй окей»
3. «давай пошагово что делать помогай»

## Где AI ошибся или было неудобно
Код экранов не собирался: Android Studio показывала ошибки `Unresolved reference 'icons'` в `EmptyState.kt`, `ItemCard.kt` и `ListScreen.kt`. AI использовал `Icons.Default.Favorite` и `Icons.Default.Search`, но не сказал, что для них нужна зависимость `material-icons-extended`. Я увидел ошибки в окне Build, спросил AI, и мы нашли причину. Я добавил строку `implementation("androidx.compose.material:material-icons-extended")` в `app/build.gradle.kts` и нажал Sync Now, после этого проект собрался.

Также были мелкие проблемы: `ListScreen.kt` оказался в папке `components` вместо `screens`, и я перенёс его через Refactor. В `ListScreen` пропал импорт `PlaceTopBar`, я добавил его через Alt+Enter.

## Что менял и писал сам
- создал проект в Android Studio, пакеты `data`, `ui.components`, `ui.screens` и файлы;
- сам настроил git, сделал коммиты, создал репозиторий на GitHub и запушил;
- добавил в проект фотографии (`park`, `museum`, `cafe`);
- исправлял ошибки импортов и расположения файлов, запускал приложение на эмуляторе и проверял каждый экран;
- сделал скриншоты в светлой и тёмной темах и README;
- перед защитой разобрал файлы проекта, чтобы объяснить каждый из них.

Эскиз сделан с помощью Claude, я проверил его и принял за основу.

---

# SIS 4: рефакторинг на MVVM

## Какие AI-инструменты использовал и для чего
Использовал Claude:
- прочитал задание SIS4 и предложил план архитектуры (файл `design/architecture.md`);
- переписал приложение из SIS3 на MVVM: `PlacesRepository`, `PlacesUiState`, `PlacesViewModel`, Route-композаблы и stateless-экраны, превью четырёх состояний, логи жизненного цикла;
- написал черновики README и этого файла;
- помог разделить работу на коммиты: план, репозиторий, ViewModel, экраны, логи, документация.

## 3 самых полезных промпта
1. «i need to complete this but complete it for easy defence every parametr easy to explain and understand because last time a lose my 30 points in defence because of easy thigns» (с PDF задания SIS4)
2. Отправил архив проекта `AlmatyPlaces.zip` (мой код из SIS3), чтобы AI рефакторил именно его.

## Где AI поставил логику не в тот слой (и как я это исправил)
В моём коде из SIS3 (который писал AI) фильтрация лежала прямо внутри composable. В `ListScreen` было:

```kotlin
var selected by remember { mutableStateOf("Все") }
val filtered = if (selected == "Все") places else places.filter { it.category == selected }
```

Здесь сразу две ошибки слоя: выбранная категория жила в `remember`, поэтому терялась при повороте экрана, а фильтрация (бизнес-логика) выполнялась внутри UI. То же было в `FavoritesScreen` (`places.filter { it.id in favoriteIds }`), а избранное хранилось в `remember` в `AppNavigation`.

Что я сделал:
- вынес `selectedCategory`, `searchQuery` и `favoriteIds` в `PlacesUiState`;
- перенёс фильтрацию и поиск в функцию `withLists()` в `PlacesViewModel`, экран теперь получает готовые `visiblePlaces` и `favoritePlaces`;
- экраны стали stateless: получают `uiState` и лямбды, а `Route` соединяет их с ViewModel.

## Где AI ошибся или было неудобно
Проверка кода без сборки: AI не смог собрать проект в своей среде (нет доступа к Maven), поэтому код проверялся только чтением. Любая ошибка компиляции нашлась бы только в Android Studio, поэтому сборку и поворот экрана я проверяю сам на эмуляторе.

Также в данных SIS3 у первого места категория была записана как `"<Парки>"` (с угловыми скобками), из-за этого оно не попадало в фильтр «Парки». Я исправил это в `PlacesRepository`.

## Что делал сам
- запускаю приложение на эмуляторе, выполняю rotation test (поиск, фильтр и избранное остаются после поворота);
- проверяю состояния loading, error (иконка «жука» и «Повторить») и empty (категория «Бары»);
- копирую настоящий Logcat в README;
- разбираю файлы перед защитой, чтобы объяснить каждый слой и путь одного клика;
- делаю push в GitHub.

