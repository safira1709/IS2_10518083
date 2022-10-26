<?php
require_once('koneksi.php');
$id
= $_GET['id'];
$sql = "SELECT * FROM contact WHERE id=$id";
$r
= mysqli_query($con,$sql);
$result = array();
$row = mysqli_fetch_array($r);
array_push($result,array(
"id"=>$row['id'],
"nama"=>$row['nama'],
"nohp"=>$row['nohp'],
"angkatan"=>$row['angkatan'],
"kelas"=>$row['kelas']
));
echo json_encode(array('result'=>$result));
mysqli_close($con);
?>