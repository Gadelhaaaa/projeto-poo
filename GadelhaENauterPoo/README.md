# VampireClone

Um projeto libGDX gerado com o gdx-liftoff.

Este projeto foi gerado com um template que inclui inicializadores simples de aplicativo e uma extensão de ApplicationAdapter que desenha o logo do libGDX.

Plataformas

core: Módulo principal com a lógica do aplicativo compartilhada por todas as plataformas.
lwjgl3: Plataforma principal de desktop usando LWJGL3; chamada de 'desktop' nas documentações mais antigas.
Gradle Este projeto usa o Gradle para gerenciar dependências. O wrapper do Gradle foi incluído, então você pode rodar tarefas do Gradle usando os comandos gradlew.bat ou ./gradlew. Tarefas e flags úteis do Gradle:

--continue: com essa flag, os erros não irão interromper a execução das tarefas.
--daemon: com essa flag, o daemon do Gradle será usado para rodar as tarefas escolhidas.
--offline: com essa flag, os arquivos de dependências armazenados em cache serão usados.
--refresh-dependencies: essa flag força a validação de todas as dependências. Útil para versões snapshot.
build: compila os fontes e arquiva os projetos.
cleanEclipse: remove os dados do projeto do Eclipse.
cleanIdea: remove os dados do projeto do IntelliJ.
clean: remove as pastas de build, que armazenam as classes compiladas e arquivos de build.
eclipse: gera os dados do projeto para o Eclipse.
idea: gera os dados do projeto para o IntelliJ.
lwjgl3:jar: compila o jar executável do aplicativo, que pode ser encontrado em lwjgl3/build/libs.
lwjgl3:run: inicia o aplicativo.
test: executa testes unitários (se houver).
Observe que a maioria das tarefas que não são específicas para um único projeto pode ser executada com o formato nome:prefixo, onde o nome deve ser substituído pelo ID de um projeto específico. Por exemplo, core:clean remove a pasta de build apenas do projeto core.

Note that most tasks that are not specific to a single project can be run with `name:` prefix, where the `name` should be replaced with the ID of a specific project.
For example, `core:clean` removes `build` folder only from the `core` project.
