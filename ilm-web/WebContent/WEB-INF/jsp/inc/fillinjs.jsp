<jsp:include page="js.jsp" />

<script type="text/javascript">
var fillin = {
	ruleSel: function() {
		var rule = $("#report-name").find(":selected").val();
		return  rule;
		},
    getRule: function() {
        $("#report-name").find('option').remove();
        
        $.post('/account/regu/getRuleToRegu.html', {'reguNo':$("#regu-select").find(":selected").val()}).done(function(json) {
                console.log(json);
                var size = json.data.length;

                for (var i = 0; i < size; i++) {
                    // var rule = $.parseJSON(json[i]);
                    // if (rule.property == 'manual') {
                        $("#report-name").append(
                                $("<option></option>").attr("value", json.data[i][0])
                                        .text(json.data[i][0] + ": " + json.data[i][1]));
                    // }
                }
            });
    },
    postPass: function(rule) {
    	$.post('fillinRule.html', {'oper':'pass', 'rule':rule , 'attach':$('#file-upload').val()}).done(function(json) {
    	});
    },
    postViolate: function(rule) {
        $.post('fillinRule.html', {'oper':'violate', 'rule':rule , 'attach':$('#file-upload').val()}).done(function(json) {
        });
    }
};




	$.post('/account/regu/getAllRegulation.html').done(
			function(json) {
				console.log(json);
				var size = json.data.length;
				for (var i = 0; i < size; i++) {
					$("#regu-select").append(
							$("<option></option>").attr("value",
									json.data[i][0]).text(
									json.data[i][0] + ": " + json.data[i][2]));
				}

			});

	// global bad smell
	
	
	$("#regu-select").change(function() {
		fillin.getRule();
	});
	
	$('#pass').click(function() {
		var rule = fillin.ruleSel();
		fillin.postPass(rule);
	});
	
	$('#violate').click(function() {
	      var rule = fillin.ruleSel();
		  fillin.postViolate(rule);	           
	});
	
</script>
