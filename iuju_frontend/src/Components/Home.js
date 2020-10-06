  
import React from 'react';
import {Link, withRouter} from "react-router-dom";
import '../App.css';
import { useHistory } from "react-router-dom";
import {Container,Row, Col, Card} from 'react-bootstrap'
import axios from 'axios'
import moment from 'moment/min/moment-with-locales';
const { ipcRenderer } = window.require('electron');

 class Home extends React.Component {
   constructor(props){
     super(props)
     this.state= {
       events : [],
       notified: false,
     };
     this.urlGetEventsOfTheDay = 'http://localhost:8083/api/eventos/search'
   }
componentDidMount(){
  this.props.setHelp(false)
  this.props.setShowNav(true)
  const date = new Date();
  const dateFormated = moment(date).format().split('T')[0]
    axios({
      method: 'get',
      url: this.urlGetEventsOfTheDay,
      params: {
        fecha: dateFormated
      }
    })
    .then(res =>{
      this.setState({events: res.data});
      this.evaluateNotification()
    })
    .catch(function (error) {
      console.log(error);
    });

}

evaluateNotification(){
  if(this.state.events && !this.state.notified){
    var body= ""
    this.state.events.forEach(e => {body+= "Nombre: " + e.nombre + "\n" + "Fecha: " + moment(e.fechaInicio).format('LLL')+ "\n"})
    console.log(body.toString())
    ipcRenderer.send('send-notification-event', body)
    ipcRenderer.on('sended-notification-event', function(_event,arg){
      console.log(arg)
    })
    this.setState({notified:true})
  }
 /* if(tasks){
    ipcRenderer.send('send-notification-task')
  }*/
}

  parseDate(date) {
    moment.locale('es')
    const date_moment = moment(date);
    return  date_moment.format('LLL');
}
  render(){
   const itemsEvents = this.state.events?.map((item_e, key) =>
    <Card  key={item_e.id} >
      <Card.Body>
          <Card.Title>{item_e.nombre}</Card.Title>
          <Card.Subtitle>Inicio: {this.parseDate(item_e.fechaInicio)}</Card.Subtitle>
          <br/>
          <Card.Subtitle>Fin: {this.parseDate(item_e.fechaFin)}</Card.Subtitle>
          <br/>
          <Card.Text>{item_e.descripcion}</Card.Text>
      </Card.Body>
  </Card>
  );

  return(
    <div className= "page">
      
    <Container fluid>
      <Row>
        <Col><h3>Next Tasks</h3></Col>
        <Col><h3>Next Events</h3>
          {itemsEvents}
        </Col>

      </Row>
      <Row>
        <Col><h3>Notes</h3></Col>
      </Row>
    </Container>
    </div>
  );
 }
}

export default  withRouter (Home);