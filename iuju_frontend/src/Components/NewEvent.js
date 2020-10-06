  
import React,  { useState } from 'react';
import {Link, withRouter} from "react-router-dom";
import '../App.css';
import { useHistory } from "react-router-dom";
import {Container,Row, Form, FormLabel, Col, Jumbotron, Button} from 'react-bootstrap'
import DatePicker from 'react-datepicker'
import "react-datepicker/dist/react-datepicker.css";
import axios from 'axios'
import moment from 'moment'
const { ipcRenderer } = window.require('electron');
const urlEvent = 'http://localhost:8083/api/eventos';


 function NewEvent(props){
  const history = useHistory();
  const [text, setText] = useState("text")
  const [startDate, setStartDate] = useState(new Date())
  const [endDate, setEndDate] = useState(new Date())


  let handleColor = time => {
    return time.getHours() > 12 ? "text-success" : "text-error";
  };

  function handleSubmit(event){
    event.preventDefault()
    const form = event.target.elements
    axios({
        method: 'post',
        url: urlEvent,
        data:{
            'nombre': form.nombre.value,
            'fechaInicio': parseDate(startDate),
            'fechaFin': parseDate(endDate),
            'descripcion': form.descripcion.value,
            'urgencia': form.urgencia.value,
            'categoria': form.categoria.value
        }
    })
    .then(res => {
        ipcRenderer.send('hide-window')
    })
    .catch(function (error) {
        console.log(error);
      });
    
}
/* Function to parse the date */
function parseDate(date) {
    moment.locale('es')
    const date_moment = moment(date);
    return  date_moment.format();
}

  return(

    <div className= "page">
    <Container fluid>
      <Row>
      <Col md = {{span: 6, offset:3}}> 
      <Jumbotron>
        <Form onSubmit={handleSubmit.bind(this)}>
          <Form.Group>
            <br />
            <Form.Control placeholder="Nombre" name="nombre" />
            <br />
            <FormLabel> Fecha Inicio </FormLabel>
            <br />
            <DatePicker
            name="fechaini"
            showTimeSelect
            selected={startDate}
            onChange={date => setStartDate(date)}
            timeClassName={handleColor}
            />
            <br />
            <FormLabel> Fecha Fin </FormLabel>
            <br />
            <DatePicker
            name="fechafin"
            showTimeSelect
            selected={endDate}
            onChange={date => setEndDate(date)}
            timeClassName={handleColor}
            />
            <br />
            <br />
            <Form.Control placeholder="Descripcion" name="descripcion" />
            <br />
            <Form.Control type="number" placeholder="Urgencia (1: Poco urgente, 5: Muy Urgente)" max={5} min={1} name="urgencia"/>
            <br />
            <Form.Control placeholder="Categoria" name="categoria"/>
          </Form.Group>
          <Button type="submit">Guardar</Button>
        </Form>
        </Jumbotron>
        </Col>
      </Row>
    </Container>
    </div>
  );
 }

export default  withRouter (NewEvent);