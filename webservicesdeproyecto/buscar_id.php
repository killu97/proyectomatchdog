<?php

include 'conexion.php';
$correo_c=$_POST['correo'];
$password_c=$_POST['contrasena'];

//$correo_c='alex21';
//$password_c='alex';

$consulta="SELECT * from users WHERE correo='$correo_c' AND contrasena='$password_c'";
$resultado=$conexion->query($consulta);

while($fila=$resultado -> fetch_array()){
	$users[]=array_map('utf8_encode', $fila);
	//$users[]=$fila;
}

echo json_encode($users);
$resultado->close();
?>

