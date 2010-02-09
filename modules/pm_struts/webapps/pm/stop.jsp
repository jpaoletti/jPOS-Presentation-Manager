<!--/*
 * jPOS Presentation Manager [http://jpospm.blogspot.com]
 * Copyright (C) 2010 Jeronimo Paoletti [jeronimo.paoletti@gmail.com]
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */-->
 #set ($msg = $request.getParameter("msg"))
#if (!$msg)
 #set ($msg = "Access denied")
#end

<div class="span-10" align="center">
<img src="$request.contextPath/images/police_stop.gif">
<h1 class="red">$msg</h1>
<p align="left">
 <b>Dear $user.name,</b>
</p>
<p align="justify">
 <i>We are sorry, but your permission set doesn't allow 
 you to access the requested page. If you believe that this
 is a mistake, please contact the site administrator.</i>
</p>

<p align="right">
 <b>--Webmaster</b>
</p>
</div>

