<jsp:include page="parciales/header.jsp" />

<!--
Deber�n de crear un formulario en el cual digitalizan:
nombre
edad o boleta
y un mensaje
Despu�s de ello deber� de existir un bot�n para guardar esos datos y de ah� firmar el documento
Para poder firmar el documento les solicitara en un formulario la clave privada (aqu� pueden hacer uso de los .key que les ense�e a realizar)
Una vez que se introduce se genera el PDF con la informaci�n que se introdujo + la visualizaci�n de la firma en la parte inferior del PDF.

-->

<div class="container">
    <div class="row mt-2">
        <div class="col">
            <div class="card">
                <div class="card-body">
                    <form action="Obtenerdatos" method="post">
                        <h2>Datos:</h2>
                        <div class="form-group">
                            <input type="text" name="name" placeholder="Nombre" class="form-control">
                        </div>
                        <div class="form-group">
                            <input type="number" name="old" placeholder="Edad" class="form-control">
                        </div>
                        <div class="form-group">
                            <input type="text" name="msj" placeholder="Mensaje" class="form-control">
                        </div>
            
                        <button type="submit" class="btn btn-primary" >
                            Guardar datos
                        </button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="parciales/footer.jsp" />