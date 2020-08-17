<?php

include 'conexion.php';
$codigo=$_POST['codigo'];
$nombre=$_POST['nombre'];
$edad=$_POST['edad'];
$correo=$_POST['correo'];
$contrasena=$_POST['contrasena'];
$num_cel=$_POST['num_celular'];
$can_nom=$_POST['can_nom'];
$can_edad=$_POST['can_edad'];
$raza=$_POST['raza'];
$color=$_POST['color'];
$sexo=$_POST['sexo'];
$foto=$_POST['foto'];

$path="imagenesc/$correo.jpg";
//$url="http://192.168.0.107:80/webservicesdeproyecto/$path";
$url = "imagenesc/".$correo.".jpg";

file_put_contents($path, base64_decode($foto));
$bytesArchivo=file_get_contents($path);

if(buscarrepetido($correo,$conexion)==1){
	echo("Existe");
}else{
$sql="INSERT INTO users VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
	$stm=$conexion->prepare($sql);
	$stm->bind_param('isissssissss',$codigo,$nombre,$edad,$correo,$contrasena,$num_cel,$can_nom,$can_edad,$raza,$color,$sexo,$bytesArchivo);
		
	if($stm->execute()){
		echo "registra";
	}else{
		echo "noRegistra";
	}
	mysqli_close($conexion);
	}

function buscarrepetido($email,$conexion){
	$sql="SELECT *from users where correo='$email'";
	$result=mysqli_query($conexion,$sql);

	if(mysqli_num_rows($result)>0){
		return 1;
	}else{
		return 0;
	}
}

?>