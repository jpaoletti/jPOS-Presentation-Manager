/*
 * plugin jquery: Ajax Real Time Extension
 *
 * @author arthur.obriot
 * 
 * @version 1.5
 * project site: http://plugins.jquery.com/project/Arte
 * developing website: http://code.google.com/p/arte/
 *
 */

(function() {

	var _config 	= null;
	var _is_started = false;
	var _list_xml_actions = new Array();
	var _list_html_node = new Array();

	// manage parameters
	// this function test if parameters are not already initialized
	function _manage_parameter(setting) {
		var config = {
			'time':				1000,	// int			Timer tick beetwen each round
			'ajax_mode':		'POST',	// GET|POST		like the jquery ajax data_mode
			'ajax_type':		'text',	// text|xml 	like the jquery ajax data_type
			'ajax_url':			'',		// url of the ajax request
			'on_data_set':		null,	// routine which has to be called before the ajax request (usefull to set ajax parameter). It has to return a string like "arg1=toto&arg2=titi"
			'on_success':		null	// routine(text, xml) which will be called when the loop end a round
		};
		
		// is it the first time we have called arte ?
		if (_config == null)
		{
			// if the user has custom options, update ours
			if (setting)	$.extend(config, setting);
			
			// set the options of configuration as definitly set
			_config = config;
		}
	}
	
	// start the action loop
	function _start()
	{
		if (_is_started == false)
		{
			_is_started = true;
			_launch_loop();
		}
		return this;
	}
	
	// stop the action loop
	function _stop()
	{
		if (_is_started == true)	_is_started = false;
		return this;
	}
	
	// start or stop the action loop, with alternance
	function _toogle()
	{
		if (_is_started == true)
			_is_started = false;
		else
		{
			_is_started = true;
			_launch_loop();
		}
	}
	
	// used to manage parameters after the initialisation of arte
	function _set(key, value)
	{
		if (_config[key])
			_config[key] = value;
		return this;
	}
	
	// used to get parameter values
	function _get(key)
	{
		if (_config[key])
			return _config[key];
		if (key == "nb_node")
			return _list_xml_actions.length;
		if (key == "nb_elt")
			return _list_html_node.length;
	}
	
	// main routine, used to execute one round more
	function _launch_loop()
	{
		// verify we are allowed to launch a new round
		if (_is_started == false) return;
		
		// create the next package to send
		var ajax_data = (_config['on_data_set']) ? _config['on_data_set']() : "";
		
		// set the ajax request
		$.ajax({
			type: 		_config['ajax_mode'],
			url: 		_config['ajax_url'],
			data: 		ajax_data,
			dataType: 	_config['ajax_type'],
			success: 	function(data, textStatus){
				// execute the custom list of action with some xml nodes
				if (_list_xml_actions.length > 0)
					_execute_custom_xml_actions(data);
				// auto refresh the list of html element
				if (_list_html_node.length > 0)
					_execute_custom_html_nodes(data);
				// call the final success function
				if (_config['on_success'])	
					_config['on_success'](data);
			}
		});
		
		// launch automatically a new cycle
		setTimeout("$.arte().launch()", _config['time']);
	}
	
	// this function, which takes the xml response from the ajax query, parse the response
	// to execute user action
	function _execute_custom_xml_actions(data_xml)
	{
		for (i = 0; i < _list_xml_actions.length; i++)
			$(data_xml).find(_list_xml_actions[i].node_name).each(function (){
				if (_list_xml_actions[i].fct)
					_list_xml_actions[i].fct(this);
			});
	}
	
	// this function, which takes the xml response from the ajax query, parse the response
	// to set automatically some custom html fields
	function _execute_custom_html_nodes(data_xml)
	{
		for (i = 0; i < _list_html_node.length; i++)
		{
			$(data_xml).find(_list_html_node[i].node_name).each(function (){
				$(_list_html_node[i].elt).text($(this).text());
			});
		}
	}
	
	// this function is used to add a custom action to be executed after a success ajax request
	// 'node_name' is name of the xml node we want to get the value
	// 'fct' the function which will be called if we find this node
	function _add_custom_xml_actions(node_name, fct)
	{
		// test if the element doesn't already exist.
		for (i = 0; i < _list_xml_actions.length; i++)
			if (_list_xml_actions[i].node_name == node_name)
				return this;
		
		// add the element in the tab
		_list_xml_actions.push(new _XmlItem(node_name, fct));
		return this;
	}
	
	// same as above but remove the node_name from the action list
	function _del_custom_xml_actions(node_name)
	{
		// rebuild a new clean action list
		var newtab = new Array();
		for (i = 0; i < _list_xml_actions.length; i++)
			if (_list_xml_actions[i].node_name != node_name)
				newtab.push(_list_xml_actions[i]);
		// update the custom list
		_list_xml_actions = newtab;

		return this;
	}

	$.arte = (function (settings)
	{
		// manage parameters
		_manage_parameter(settings);
		
		// make public links with hidden functions
		return {
			start:			_start,
			is_started:		_is_started,
			stop:			_stop,
			toogle:			_toogle,
			set:			_set,
			get:			_get,
			launch:			_launch_loop,
			add_action:		_add_custom_xml_actions,
			del_action:		_del_custom_xml_actions
		};
	});
	
	// this function is used to make an auto update of a custom html field
	// 'node_name' is name of the xml node we want to use to udpate the html field
	// if no arguement is passed, stop the auto refresh for these nodes
	$.fn.arte = (function (node_name)
	{
		// parse the list of html node
		this.each(function() {
			if (node_name)
			{
				// add the element, and test if the element doesn't already exist
				var is_in = false;
				for (i = 0; i < _list_html_node.length; i++)
					if (_list_html_node[i].elt == this)
						is_in = true;
				if (is_in == false)
					_list_html_node.push(new _HtmlAuto(this, node_name));
			}
			else
			{
				// remove the element
				var newtab = new Array();
				for (i = 0; i < _list_html_node.length; i++)
					if (_list_html_node[i].elt != this)
						newtab.push(_list_html_node[i]);
				// update the custom list
				_list_html_node = newtab;
			}
		});
	
		return this;
	});
	
	// the following class is just a container to join a string with a function
	// it will be used for the xml actions
	function _XmlItem(node_name, fct)
	{
		this.node_name = node_name;
		this.fct = fct;
	}
	
	// the following class is just a container to join an html element with a string
	// it will be used for the auto update html field 
	function _HtmlAuto(elt, node_name)
	{
		this.elt = elt;
		this.node_name = node_name;
	}
})(jQuery);