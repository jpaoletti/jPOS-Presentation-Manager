	var oTable;
	var asInitVals = new Array();
	var n = 0;
	
	function configurePopup(operationdiv) {
            var distance = 10;
            var time = 250;
            var hideDelay = 500;
 
            var hideDelayTimer = null;
 
            var beingShown = false;
            var shown = false;
            var trigger = $('.trigger', operationdiv);
            var info = $('.operationspopup', operationdiv).css('opacity', 0);
 
			//alert(operationdiv);
 
            $([trigger.get(0), info.get(0)]).mouseover(function () {
                if (hideDelayTimer) clearTimeout(hideDelayTimer);
                if (beingShown || shown) {return false;} 
				else {
                    beingShown = true;
                    info.css({
                        top: -5,
                        left: 0,
                        display: 'block'
                    }).animate({
                        top: '-=' + distance + 'px',
                        opacity: 1
                    }, time, 'swing', function() {
                        beingShown = false;
                        shown = true;
                    });
                }
 
                return false;
            }).mouseout(function () {
                if (hideDelayTimer) clearTimeout(hideDelayTimer);
                hideDelayTimer = setTimeout(function () {
                    hideDelayTimer = null;
                    info.animate({
                        top: '-=' + distance + 'px',
                        opacity: 0
                    }, time, 'swing', function () {
                        shown = false;
                        info.css('display', 'none');
                    });
 
                }, hideDelay);
 
                return false;
            });
        }
	
	function fnRowCallback ( nRow, aData, iDisplayIndex ) {
		var i = oTable.fnGetPosition( nRow );
		//var i = iDisplayIndex;
		$('td:eq(0)', nRow).html( '<div class="operations" id="row_'+i+'"><div class="operationspopup" id="g_'+i+'">error</div><div class="trigger">'+aData[0]+'</div></div>' );
		var operationdiv = $('#row_'+i, nRow);
		var grupodiv = $('#g_'+i, nRow);
		grupodiv.load('opers.do?pmid='+pmid+'&i='+i);
		configurePopup(operationdiv);
		return nRow;
	}
	
	$(document).ready(function() {
			oTable = $('#list').dataTable({
						"bProcessing": true,
						"oLanguage": {
							"sSearch": texts[0],
							"oPaginate": {
								"sFirst": texts[1],
								"sLast": texts[2],
								"sNext": texts[3],
								"sPrevious": texts[4] 
							},
							"sInfo": texts[5],
							"sInfoEmpty": texts[6],
							"sInfoFiltered": texts[7],
							"sLengthMenu": texts[8],
							"sProcessing": texts[9],
							"sZeroRecords": texts[10]
						},
						"bSort": sortable,
						"bFilter": searchable,
						"bPaginate": paginable,
						"bInfo":false, 
						"bAutoWidth":false
			});
			
	 		$("tbody tr").hover(function() {  
				$(this).addClass('row_selected');  
			}, function() {  
				$(this).removeClass('row_selected');  
			} );  

	 		if(searchable){
				$("tfoot input").keyup( function () {
					/* Filter on the column (the index) of this element */
					oTable.fnFilter( this.value, $("tfoot input").index(this)+n );
				} );
			
				$("tfoot input").each( function (i) {
					asInitVals[i+n] = this.value;
				} );
			
				$("tfoot input").focus( function () {
					if ( this.className == "search_init" ){
						this.className = "";
						this.value = "";
					}
				} );
			
				$("tfoot input").blur( function (i) {
					if ( this.value == "" )	{
						this.className = "search_init";
						this.value = asInitVals[$("tfoot input").index(this)+n];
					}
				} );
			}
			
	} );