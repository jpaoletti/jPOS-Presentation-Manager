
function topmenu(selected){
   var topmenuul=$("#topmenu");
   topmenuul.append('<li id="topmenu1"><a href="index.html">Home</a></li>');
   topmenuul.append('<li id="topmenu2"><a href="download.html" title="Downloads">Downloads</a></li>');
   topmenuul.append('<li id="topmenu3"><a href="licence.html" title="Licence">licence</a></li>');
   $("#topmenu"+selected).addClass("selected");
}

function header(){
   var wrap = $("#wrap");
   wrap.append('<div id="header">');
   wrap.append('<div id="logo"><h1><a href="#">jPOS PM</a></h1></div>');
   wrap.append('<div id="nav"><ul id="topmenu"></ul></div><div class="clear"></div>');
   wrap.append('</div>');
}

function sidebar(){
   var page = $("#page");
   page.append('<div id="main-sidebar" />');

   var mainsidebar = $("#main-sidebar");
   mainsidebar.append('<div class="sidebar-box brown-box"><p>jPOS PM helps you to...</p></div>');

   mainsidebar.append('<div id="sidebar1" class="sidebar-box" />');
   $("#sidebar1").append('<h4>Links</h4><ul id="ulsidebar1"></ul>');
   $("#ulsidebar1").append('<li><a href="http://jpospm.blogspot.com/" title="jPOS PM Blog" target="_blank">Blog</a></li>');
   $("#ulsidebar1").append('<li><a href="http://github.com/jpaoletti/jPOS-Presentation-Manager" title="GitHub Project" target="_blank">GitHub Project</a></li>');
   $("#ulsidebar1").append('<li><a href="http://wiki.github.com/jpaoletti/jPOS-Presentation-Manager/" title="Wiki" target="_blank">Wiki</a></li>');


   //mainsidebar.append('<div id="sidebar2" class="sidebar-box" />');
   //$("#sidebar1").append('<h4>Sponsors</h4><ul id="ulsidebar2"></ul>');
   //$("#ulsidebar2").append('<li><a href="http://www.spyka.net" title="spyka Webmaster resources">spyka webmaster</a></li>');
   //$("#ulsidebar2").append('<li><a href="http://www.justfreetemplates.com" title="free web templates">JustFreeTemplates</a></li>');

   page.append('<div class="clear"></div>');
}

function footer(){
   var wrap = $("#wrap");
   wrap.append('<div class="footer"><div class="footer-level-2"><p id="footerlist"></p></div></div>');
   var footerlist = $("#footerlist");
   footerlist.append('<a href="index.html">Home</a>');
   footerlist.append('<a href="download.html" title="Downloads">Downloads</a>');
   footerlist.append('<a href="licence.html" title="Licence">licence</a>');
}

function pageend(){
   var wrap = $("#wrap");
   wrap.append('<div id="pageend" class="page-end"></div>');
   $("#pageend").append('<p>&copy; 2008 mySite. Design: <a href="http://www.spyka.net" title="spyka Webmaster">spyka webmaster</a> available from <a href="http://www.justfreetemplates.com" title="free css templates">Just Free Templates</a>. Valid <a href="http://validator.w3.org/check/referer" title="valid XHTML strict">XHTML</a> and <a href="http://jigsaw.w3.org/css-validator/check/referer" title="CSS">CSS</a></p>');
}

