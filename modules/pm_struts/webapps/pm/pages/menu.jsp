<%@page import="org.jpos.ee.pm.menu.*" %>
<%@page import="org.jpos.ee.pm.core.*" %>
<%@page import="org.jpos.ee.pm.struts.*" %>
<bean:define id="es" name="es" type="org.jpos.ee.pm.struts.PMEntitySupport" />
<%!
protected void printMenu(final Menu m, final JspWriter out, final HttpSession session){
    try{
    final PMEntitySupport es = (PMEntitySupport) session.getAttribute("es");
    //Base case
     if( m instanceof MenuItem ){
         final MenuItem item = (MenuItem) m;
         out.print("<li>");
         if(item.getLocation() == null){
             out.print("<a href='#'>"+PresentationManager.getMessage(m.getText())+"</a>");
         }else{
             final MenuItemContext ctx = (MenuItemContext)item.getLocation().build(item,es.getContext_path());
             out.print(ctx.getPrefix());
             out.print(PresentationManager.getMessage(ctx.getValue()));
             out.print(ctx.getSufix());
         }
         out.print("</li>");
     }else{
         final MenuList list = (MenuList) m;
         out.print("<li>");
         out.print("<a href='#'>"+PresentationManager.getMessage(m.getText())+"</a>");
         out.print("<ul>");
         for(Menu sm : list.getSubmenus()){
             printMenu(sm, out, session);
         }
         out.print("</ul>");
         out.print("</li>");
     }
    }catch(Exception e){
        PresentationManager.getPm().error(e);
    }
}
%>
<div id="menu" class="jqueryslidemenu">
    <ul>
        <%
        final PMSession pmsession = (PMSession) session.getAttribute("pmsession");
        if(pmsession != null && pmsession.getMenu() != null){
            final MenuList list = (MenuList) pmsession.getMenu();
            for(Menu m : list.getSubmenus()){
                printMenu(m, out, session);
            }
        }
        %>
    </ul>
</div>