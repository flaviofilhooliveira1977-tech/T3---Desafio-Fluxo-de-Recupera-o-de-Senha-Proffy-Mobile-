# Proffy Mobile - Fluxo "Esqueci minha senha"

## Identificação

- **Aluno:** Flávio Lourenço
- **Turma:** Sistemas de Informação - 6º período

## Sobre o projeto

Aplicativo Android em Kotlin baseado no projeto [proffy-mobile-2026](https://github.com/Santosfael/proffy-mobile-2026). Esta entrega implementa o fluxo de recuperação de senha, com validação de formulário e navegação para a tela de conclusão já existente (`FinallyActivity`).

## Como foi implementado

A tela `ForgotActivity` (layout `activity_forgot.xml`) valida o e-mail enquanto o usuário digita, usando um `addTextChangedListener` e o `Patterns.EMAIL_ADDRESS` do Android, e também ao sair do campo, com um `setOnFocusChangeListener`, seguindo o mesmo padrão das telas de cadastro. Enquanto o e-mail está vazio ou inválido, o botão fica cinza, desabilitado e com o texto "Entrar". Quando o e-mail é válido, o botão fica verde, habilitado e com o texto "Enviar". Para e-mails em formato inválido, a mensagem de erro aparece abaixo do campo, e todos os textos ficam em `strings.xml`. Ao tocar em "Enviar" com um e-mail válido, a `ForgotActivity` abre a `FinallyActivity` por `Intent`, enviando como extras o título, o subtítulo e o texto do botão ("Redefinição enviada!", a mensagem de confirmação e "Voltar ao login"), e encerra a si mesma com `finish()`. Na `FinallyActivity`, o botão "Voltar ao login" abre a `LoginActivity` com as flags `FLAG_ACTIVITY_NEW_TASK` e `FLAG_ACTIVITY_CLEAR_TASK`, limpando a pilha de navegação. O acesso ao fluxo é feito pelo botão "Esqueci minha senha" da tela de login, e o teclado não cobre o botão graças ao `ScrollView` no layout e ao `windowSoftInputMode="adjustResize"` registrado no `AndroidManifest.xml`.

## Estrutura do fluxo

```
LoginActivity  ->  ForgotActivity  ->  FinallyActivity  ->  LoginActivity
 (Esqueci minha     (valida o e-mail     (Redefinição        (pilha limpa)
  senha)             e envia)             enviada!)
```

## Como executar

1. Clone o repositório e abra a pasta no Android Studio.
2. Aguarde a sincronização do Gradle.
3. Execute o app em um emulador ou dispositivo Android.
