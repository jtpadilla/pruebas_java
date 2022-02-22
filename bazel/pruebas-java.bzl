load("@rules_jvm_external//:defs.bzl", "maven_install")
load("@bazel_tools//tools/build_defs/repo:git.bzl", "git_repository")

JUNIT_VERSION = "4.12"

def pruebas_java_deps():
    #
    maven_install(
        artifacts = [
            "junit:junit:%s" % JUNIT_VERSION,
            "org.json:json:20210307",
        ],
        repositories = [
            "https://repo1.maven.org/maven2",
        ],
    )
