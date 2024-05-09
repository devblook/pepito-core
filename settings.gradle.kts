rootProject.name = "pepito-core"

arrayOf("api", "plugin").forEach {
    include("pepito-core-$it")
    project(":pepito-core-$it").projectDir = file(it)
}