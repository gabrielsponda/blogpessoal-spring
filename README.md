# Blog Pessoal - Backend

Este repositório contém o backend do Blog Pessoal, uma aplicação web desenvolvida com Java Spring. Ela permite aos usuários criar uma conta, autenticar-se, publicar postagens, criar temas e visualizar postagens relacionadas a um tema específico. A aplicação incorpora práticas de segurança, incluindo autenticação de usuário com tokens que expiram após uma hora.

## Funcionalidades

- **Autenticação de Usuários**: Os usuários podem criar uma conta e autenticar-se usando um nome de usuário e senha. A autenticação é necessária para criar postagens.
- **Gestão de Postagens**: Após a autenticação, os usuários podem criar, editar e excluir suas próprias postagens.
- **Gestão de Temas**: Os usuários podem criar temas que podem ser associados às postagens.
- **Associação de Temas a Postagens**: Cada postagem pode ser associada a um tema, facilitando a organização e a busca por postagens relacionadas.
- **Visualização de Postagens por Tema**: Os usuários podem visualizar todas as postagens associadas a um tema específico.
- **Segurança e Expiração de Sessão**: A sessão do usuário expira após uma hora, requerendo nova autenticação.

## Tecnologias Utilizadas

- **Java Spring**: Para o backend, incluindo Spring Security para autenticação e autorização.
- **MySQL**: Banco de dados utilizado para armazenar os dados da aplicação.
- **Insomnia**: Utilizado para testar as APIs desenvolvidas antes de integrar com o frontend.
- **Deploy no Render**: A aplicação backend está hospedada no Render e pode ser acessada através do link [https://educajunto.onrender.com/](https://educajunto.onrender.com/).

## Frontend

O frontend da aplicação foi desenvolvido utilizando React e pode ser encontrado no seguinte repositório: [https://github.com/gabrielsponda/blogpessoal-react](https://github.com/gabrielsponda/blogpessoal-react).
