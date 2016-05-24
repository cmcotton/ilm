<jsp:include page="js.jsp" />
<!-- Page-Level Demo Scripts - Tables - Use for reference -->
<script>
	$(document).ready(function() {

		var table = $('#dataTables-example').DataTable({
			"ajax" : 'getAllRegulation.html',
			responsive : true
		});

		$('#dataTables-example tbody').on('click', 'tr', function() {
			$(this).toggleClass('selected');
		});

		$('#btn-apply').click(function() {
			var selrows = table.rows('.selected').data(), regus = [];
			console.log(selrows);

			for (var i = 0; i < selrows.length; i++) {
				regus.push(selrows[i][0]);
			}

			$.post('modifyApplicable.html', {
				'oper' : 'apply',
				'regus' : regus
			}).done(function(json) {
				alert('done');
			});
		});

		$('#btn-not-apply').click(function() {
			var selrows = table.rows('.selected').data(), regus = [];
			console.log(selrows);

			for (var i = 0; i < selrows.length; i++) {
				regus.push(selrows[i][0]);
			}

			$.post('modifyApplicable.html', {
				'oper' : 'notapply',
				'regus' : regus
			}).done(function(json) {
				alert('done');
			});
		});
	});
</script>