# Shopee Divulga AI

Projeto Android nativo em Kotlin + Jetpack Compose.

## Estrutura
- `app/src/main/java/com/shopeedivulgaai/MainActivity.kt`: telas do aplicativo.
- `app/build.gradle.kts`: dependências e configuração Android.
- `.github/workflows/android.yml`: compilação automática do APK no GitHub Actions.

## Como gerar o APK
1. Envie o conteúdo desta pasta para a raiz do repositório GitHub (não envie apenas o ZIP).
2. Abra a aba **Actions**.
3. Execute **Build Android APK** ou aguarde uma compilação após um push.
4. Baixe `ShopeeDivulgaAI-debug-apk` em **Artifacts** quando a execução terminar.

## Observação
Esta é uma versão inicial/protótipo. O cadastro de produtos e o gerador de roteiro funcionam localmente como demonstração. Busca real de produtos, integração de afiliados, geração de vídeo/voz e publicação automática ainda precisam de integrações próprias e autorizadas. Não informe senha, cookie ou código de acesso da Shopee no aplicativo.
