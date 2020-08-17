<?php
include 'conexion.php';

$json=array();

$raza=$_GET['raza'];
$sexo=$_GET['sexo'];
$can_nom=$_GET['can_nom'];

$consulta="SELECT can_nom, can_edad, raza, sexo, foto, user_id from users WHERE raza='$raza' OR sexo='$sexo' OR can_nom='$can_nom'";
$resultado=$conexion->query($consulta);

while($registro=mysqli_fetch_array($resultado)){
			$result["can_nom"]=$registro['can_nom'];
			$result["can_edad"]=$registro['can_edad'];
			$result["raza"]=$registro['raza'];
			$result["sexo"]=$registro['sexo'];
			$result["foto"]=base64_encode($registro['foto']);
			$result["user_id"]=$registro['user_id'];
			$json['mascotas'][]=$result;
		}

echo json_encode($json);
$resultado->close();
?>