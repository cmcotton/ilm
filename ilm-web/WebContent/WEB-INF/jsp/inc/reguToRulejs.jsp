<jsp:include page="js.jsp" />
<!-- Page-Level Demo Scripts - Tables - Use for reference -->
<script>
	$.post('/account/regu/getAllReguEntity.html').done(
			function(res) {
				console.log(res);
				var size = res.length;
				for (var i = 0; i < size; i++) {
					var json = $.parseJSON(res[i]);
					
					if (json.applicable == '1') {
						$("#regu-select").append(
	                        $("<option></option>").attr("value", json.no).text(json.no + ": " + json.name));
					}
				}

			});

	var table = $('#dataTables-example').DataTable({
		retrieve : true,
		ajax : '/account/regu/getRuleToRegu.html?reguNo=',
		responsive : true
	});

	$("#regu-select").change(
			function() {
				var reguSel = $("#regu-select").find(":selected").val();

				$.post('/account/regu/getRuleToRegu.html', {'reguNo' : reguSel}).done(function(json) {
					var obj = json.data,
					   table = $('#dataTables-example').DataTable();

					table.clear();					
					table.draw();
					obj.forEach(function(element) {
						table.row.add([element[0], element[1], element[2]]).draw();
					});
				});
			});

	$('#submit').click(function() {

	});
</script>