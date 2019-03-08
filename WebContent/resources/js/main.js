
var vm1 = new Vue({
	el: '#app',
	data: {
		produto: {
			id: '',
			nome: '',
			fabricante: '',
			quantidade: '',
			valor: '',
		},

		listaProdutos: []
	},
	
	created: function () {
		let vm = this;
		vm.listarRestProduto();
		
	},
	methods: {

		salvarProduto: function () {
			 const vm = this;
			 
			axios.post("rs/produto/salvar", vm1.produto)
				.then(response => {
					console.log(response.data);
					vm.listarRestProduto();
					vm.limparCampos();
				}).catch(function (error) {
					console.log("ERRO");
				}).finally(function () {
					console.log("FINALLY");
				});

		},
		
		listarRestProduto: function () {
			const vm = this;
			axios.get('/RestfulCRUD/rs/produto/listar')
				.then(response => {					
					console.log(response.data);
					vm.listaProdutos = response.data
				}).catch(function (error) {
					console.log(error);
				});
		},

		editarItem: function (id) { 
			 const vm = this;
			 
			 axios.get('/RestfulCRUD/rs/produto/editar/' + id)
			 .then(function (response) {
					console.log(this.produto);
					vm.produto = response.data;
			 }).catch(function (error) {
				console.log("ERRO");
			});
		 },

		excluirItem: function(id) {
			const vm = this;
			
			axios.delete('/RestfulCRUD/rs/produto/deletar/' + id)
			.then(response => {
				console.log(this.produto);
				console.log("Dado excluído com sucesso!");
				vm.listarRestProduto();
				}).catch(function (error) {
					console.log("ERRO");
				});
		},
		
		atualizarItem: function() {
			const vm = this;
			
			axios.post('/RestfulCRUD/rs/produto/update/', vm.produto)
			.then(response => {
				console.log(this.produto);
				vm.listarRestProduto();
				vm.limparCampos();				
				alert("Dados atualizados com sucesso!");
			}).catch(function (error) {
				console.log("ERRO");
			});
		},

		limparCampos: function() {
			const vm = this; 
			vm.produto = { id: '', nome: '', fabricante: '', quantidade: '', valor: '' };
		}
	}

})


