import React from 'react';
import {withRouter} from "react-router-dom";
import {Container, Row, Col} from 'react-bootstrap'
import '../App.css';
import axios from 'axios'
import moment from 'moment'
import { useEffect } from "react";
const { ipcRenderer } = window.require('electron');
const urlEvent = 'http://localhost:8083/api/eventos';



function parseDate(date) {
  moment.locale('es')
  const date_moment = moment(date);
  return  date_moment.format();
}
function handleKeyPress(target){
  const text = document.getElementById("code").value;
  if(target.charCode === 13){
    const textArray = text.split(" ")
    
    switch(textArray[0]){
      case 'nota': 
          if(textArray[1] === "-e"){
            ipcRenderer.send('open-note-extended')
          }
        break;
      case 'evento':
          if(textArray[1] === "-e"){
            ipcRenderer.send('open-event-extended')
            return;
          }
          const i_fecha = textArray.findIndex(element => element === '-d' ) + 1;
          const fecha = textArray[i_fecha];
          const i_name = textArray.findIndex(e => e === '-n' ) + 1;
          const name = textArray[i_name]; 
          const descrip_event = textArray.slice(i_name+1, textArray.length)
          
          axios({
            method: 'post',
            url: urlEvent,
            data:{
                'nombre': name,
                'fechaInicio': fecha,
                'fechaFin': null,
                'descripcion': descrip_event.toString(),
                'urgencia': 1,
                'categoria': 'general'
            }
        })
        .then(res => {
            ipcRenderer.send('hide-popup-window')
        })
        .catch(function (error) {
            console.log(error);
          });
        break;
      case 'tarea':
        if(textArray[1] === "-e"){
          ipcRenderer.send('open-tarea-extended')
          return;
        }
        const i_time = textArray.findIndex(element => element === '-t' ) + 1;
        const time = textArray[i_time];
        const i_name_t = textArray.findIndex(element => element === '-n' ) + 1;
        const name_t = textArray[i_name_t];
        const descrip = textArray.slice(i_time, textArray.length-1)
        break;
      default: 
        break;
    }
  }
}

  function Recorder(props) {
    useEffect(() => {
      props.setHelp(false)
      props.setShowNav(false)
    })
    return(
      <div className= "page">
      <Container fluid>
        <br />
        <br />
        <h5>Command</h5>
        <Row>
          <Col sm = {{span: 8, offset:2}}>
          <input type= "text" id="code" autoFocus={true} onKeyPress={handleKeyPress}></input>
          </Col>
        </Row>
      </Container>
      </div>
    );
}

export default  withRouter (Recorder);