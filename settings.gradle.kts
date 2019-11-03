rootProject.name="Sandstop"
include(":app", ":tasks", ":tasks-common")
project(":tasks").projectDir = file("features/tasks")
project(":tasks-common").projectDir = file("features/tasks-common")

