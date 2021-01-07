<jsp:include page="parciales/header.jsp" />

<!-- 
La segunda parte es la comprobación de dicha firma, para ello tienen un formulario en el cual te solicita cargar el archivo pdf
Y debe de salir una venta o un mensaje que me informe si el PDF es valido o invalido.
Esto si lo deciden hacer de forma individual.
Si es en equipo de 2, deben de agregar el servicio distribuido un cliente y un servidor.
-->

<div class="container">
    <div class="row mt-2">
        <div class="col">
            <div class="card">
                <div class="card-body">
                    <form action="Comprobar" method="post" > <!-- enctype="multipart/form-data" es para leer el archivo pero creo que no lo usare, nota mental-->
                        <h2>Validar archivo:</h2>
                        <div class="form-group">
      <label for="exampleInputFile">Subir archivo</label>
      <input type="file" class="form-control-file" name="archivo" aria-describedby="fileHelp">
      <small id="fileHelp" class="form-text text-muted">Sube el pdf firmado para verificar su autenticidad (se encuentra en la carpeta mencionada al inicio con el nombre de "datos.pdf")</small>
    </div>
                        <button type="submit" class="btn btn-primary" >
                            Verificar
                        </button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="parciales/footer.jsp" />