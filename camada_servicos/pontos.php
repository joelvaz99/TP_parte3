<?php
// Listar todas as problemas da base de dados
$app->get('/api/pontos1', function () {
	require_once ('db/dbconnect.php');
	foreach($db->problemas()
				->select('lat','longitude')
			as $row){
				
		$data[] = $row;
	}
	echo json_encode($data,JSON_UNESCAPED_UNICODE);
});

//Selecionao todos os pontos
$app->get('/api/pontos', function ($request) {
	require_once ('db/dbconnect.php');
	
		foreach($db->problemas()
				as $row){
		$data[] = $row;
			
	}
		echo json_encode($data,JSON_UNESCAPED_UNICODE);
	

});


//Selecionar por ID
$app->get('/api/ponto/{id}', function ($request) {
	$id = $request->getAttribute('id');
	$meuarray = array();
	require_once ('db/dbconnect.php');
	foreach($db->problemas()
				//->select('id','descricao')
				->where('id',$id)
			as $row
			){
		$data[] = $row;
	}
	
		echo json_encode($data,JSON_UNESCAPED_UNICODE);
	
});

//$file_path = "uploads/";
$app->post('/api/images', function ($request) {
	 $target_dir = "api/imagem/";
	 $target_file = $target_dir . uniqid() . '.'.pathinfo($_FILES['image']['name'], PATHINFO_EXTENSION);
	//$file_path = "imagem";
	//$response = getBaseURL();    
    //$file_path= $file_path . basename( $_FILES['image']['name']);
    if(move_uploaded_file($_FILES['image']['tmp_name'], $target_file)) {
       $result= ['status' => true, 'MSG' => 'sucess'];
		echo json_encode($target_file,JSON_UNESCAPED_UNICODE);
    } else{
        echo json_encode("FAIL",JSON_UNESCAPED_UNICODE);
    }

});

$app->post('/api/imagel', function ($request) {
	$file_path = "imagem/";
    $file_path=$file_path . basename( $_FILES['uploaded_file']['name']);
	
                    
   if(move_uploaded_file($_FILES['uploaded_file']['tmp_name'], $file_path)) {
        echo json_encode("Sucess",JSON_UNESCAPED_UNICODE);
    } else{
        echo json_encode("Fail",JSON_UNESCAPED_UNICODE);
    }

});

//Post - INSERIR UM PONTO
$app->post('/api/addponto/', function ($request) {
	require_once ('db/dbconnect.php');
	$descr = $_POST["descricao"];
	$lat = $_POST["lat"];
	$longitude = $_POST["longitude"];
	$user1 = $_POST["user_id"];
	
	foreach($db->user()
				->select('id')
				->where('username',$user1)
			as $row){
				
		$data[] = $row;	
		$user=$row['id'];
	}
	
	$data = array(
		"descricao" => $descr,
		"lat" => $lat,
		"longitude" => $longitude,
		"user_id" => $user
	);
	$problemas = $db ->problemas();
	$result = $problemas->insert($data);	
	if($result == null){
		$result= ['error' => false, 'mensagem' => 'Insercao falhou'];
		echo json_encode($result,JSON_UNESCAPED_UNICODE);
	}else{
		$result= ['error' => true, 'mensagem' => 'Insercao Efectuada'];
		echo json_encode($result,JSON_UNESCAPED_UNICODE);
	}
});

