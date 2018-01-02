# Capítulo 12: Spring MVC - Autenticação e Autorização

* **Cookies:** Cookies são pares de chave valor armazenados no cliente e que podem ser recebidos e enviados em requisições HTTP. As requisições HTTP não se importam com quem é quem, mas com o uso de cookies é possível com que a cada request o cliente envie um cookie dizendo quem ele é. O servidor não pode confiar cegamente no conteúdo de um cookie, pois o cliente pode alterá-lo.
* **Sessão:** Técnica que utiliza cookies para mapear (no servidor) os requests de um cliente para o objeto que representa o cliente ou outro objeto qualquer. A cada requisição o tempo de sessão é zerado e o usuário deve se autenticar novamente caso o tempo de sessão expire. Se os cookies não estiverem disponíveis, o rastreamento dos clientes deve utilizar outra técnica, como url-rewriting (passar informações específicas por meio da url, ex.: http://www.example.com/Blog/2006/12/19/).
* **Configurando um sessão com tempo limite de 3 minutos:** Basta adicionar o código abaixo ao arquivo web.xml:
```
<session-config>
    <session-timeout>3</session-timeout>
</session-config>
```
* **Método recebendo uma sessão:** Um método de mapeamento de request pode receber um objeto do tipo HttpSession, que funciona com um mapa de string em objetos java:
```
@RequestMapping("efetuaLogin")
public String efetuaLogin(Usuario usuario, HttpSession session) {
    if(new JdbcUsuarioDao().existeUsuario(usuario)) {
        session.setAttribute("usuarioLogado", usuario);
        return "menu";
    } else {
        //....
```
* **Interceptador:** Recurso semelhante aos filtros, porém possui uma referência para o controller, não possui como foco alterar os objetos request e response. Possui o método preHandle (antes da ação), posHandle (depois da ação, antes do jsp ser renderizado - pode adicionar novos objetos à view, porém não pode alterar o objeto HttpServletResponse) e afterCompletion (depois da ação e depois do jsp ser renderizado). O método preHandle deve retornar um bool para decidir se a requisição deve ou não processeguir. O interceptor pode ser definido a partir da interface HandlerInterceptor ou extendendo a classe HandlerInterceptorAdapter (já possui uma definição padrão dos métodos preHandle, posHandle e afterCompletion):
    * Exemplo de interceptor:
    ```
    public class AutorizadorInterceptor extends HandlerInterceptorAdapter {
        @Override
        public boolean preHandle(HttpServletRequest request, 
                HttpServletResponse response,
                Object controller) throws Exception {
                String uri = request.getRequestURI();
                if(uri.endsWith("loginForm") || 
                        uri.endsWith("efetuaLogin") || 
                                uri.contains("resources")){.
                    return true;
                }
                if(request.getSession().getAttribute("usuarioLogado") != null) {
                    return true;
                }
                response.sendRedirect("loginForm");
                return false;
        }
    }
    ```
    * Cadastrando o interceptor no arquivo spring-context.xml:
    ```
    <mvc:interceptors>
        <bean class="br.com.caelum.tarefas.interceptor.AutorizadorInterceptor" />
    </mvc:interceptors>
    ```
* **Logout com sessão:**
```
@RequestMapping("logout")
public String logout(HttpSession session) {
    session.invalidate();
    return "redirect:loginForm";
}
```