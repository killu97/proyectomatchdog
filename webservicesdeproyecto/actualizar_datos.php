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
$txt_userid=$_POST['user_id'];

$path="imagenesc/$correo.jpg";
$url = "imagenesc/".$correo.".jpg";

file_put_contents($path, base64_decode($foto));
$bytesArchivo=file_get_contents($path);

$sql="UPDATE users SET nombre=?,edad=?,correo=?,contrasena=?,num_celular=?,can_nom=?,can_edad=?,raza=?,color=?,sexo=?,foto=? WHERE user_id=?";
$stm=$conexion->prepare($sql);
$stm->bind_param('sissssissssi',$nombre,$edad,$correo,$contrasena,$num_cel,$can_nom,$can_edad,$raza,$color,$sexo,$bytesArchivo,$txt_userid);
if($stm->execute()){
		echo "actualiza";
	}else{
		echo "noActualiza";
	}
	mysqli_close($conexion);
	?>