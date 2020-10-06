import React, {useState, useEffect} from 'react';
import logo from './logo.svg';
import './App.css';
import {
  Switch,
  Route,
} from "react-router-dom";
import {CSSTransition, TransitionGroup} from 'react-transition-group';
import Home from './Components/Home';
import About from './Components/About'
import Recorder from './Components/Recorder'
import NavBar from './Components/NavBar'
import NewEvent from './Components/NewEvent'
import "bootstrap/dist/css/bootstrap.min.css";
import axios from 'axios'
import moment from 'moment'
const { ipcRenderer } = window.require('electron');

/*const speech = require('@google-cloud/speech');
const fs = require('fs');

/*function onListenClick(){
 fetch('http://localhost:3002/api/speech-to-text/token')
  .then(function(response) {
      return response.json();
  }).then(function (token) {
    console.log(token);
    var stream = recognizeMic(Object.assign(token, {
        objectMode: true, // send objects instead of text
        format: false // optional - performs basic formatting on the results such as capitals an periods
    }));

    stream.on('data', function(data) {
      console.log(data);
    });

    stream.on('error', function(err) {
        console.log(err);
    });

    document.querySelector('#stop').onclick = stream.stop.bind(stream);

  }).catch(function(error) {
      console.log(error);
  });
};
//
const client = new speech.SpeechClient();
// The name of the audio file to transcribe
const fileName = './resources/Grabación.m4a';

// Reads a local audio file and converts it to base64
const file = fs.readFileSync(fileName);
const audioBytes = file.toString('base64');

const audio = {
  content: audioBytes,
};
const config = {
  encoding: 'LINEAR16',
  sampleRateHertz: 16000,
  languageCode: 'en-US',
};
const request = {
  audio: audio,
  config: config,
};
async function onListenClick(){
  
  // Detects speech in the audio file
  const [response] = await client.recognize(request);
  const transcription = response.results
    .map(result => result.alternatives[0].transcript)
    .join('\n');
  console.log(`Transcription: ${transcription}`);
}
*/
function App() {
 
  const urlGetEventsOfTheDay = 'http://localhost:8083/api/eventos/search'
  const urlGetTasksOfTheHour = 'http://localhost:8083/api/tareas/search'
  const [events, setEvents] = useState([]);
  const [tasks, setTasks] = useState([]);
  const [notified, setNotified] = useState(false)
  const [help, setHelp] = useState(true)
  const [showNav, setShowNav] = useState(true)


function searchTask(){
  const date = new Date()
  axios({
    method: 'get',
    url: urlGetTasksOfTheHour,
    params: {
      hora: date.getHours() + ':' + date.getMinutes()
    }
  })
  .then(res =>{
    setEvents(res.data);
  })
  .catch(function (error) {
    console.log(error);
  })
}

  
  return (

    <div className="App">
      {/*<header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />      
  </header>*/}
      {showNav? <NavBar></NavBar> : null }
      <Route render={({location}) => (
          <TransitionGroup>
          <CSSTransition
            key = {location.key}
            timeout = {450}
            classNames = "fade"
          >
            <Switch location= {location}>
              <Route path="/home" component={() => <Home  setHelp={(help) => setHelp(help)} setShowNav={(showNav) => setShowNav(showNav)}/> } />
              <Route path="/recorder" component={() => <Recorder setHelp={(help) => setHelp(help)} setShowNav={(showNav) => setShowNav(showNav)}/> } />
              <Route path="/about" component={() => <About /> } />
              <Route path="/newevent" component={() => <NewEvent /> } />
            </Switch>
            </CSSTransition>
          </TransitionGroup>

        )}>
        </Route>
        {help ? 
        <div>
        <h5>Manejo de Recorder</h5>
          <h6>
            <ul>
            <li>Alt+1: Sacar a iuju! de la bandeja</li>
            <li>Alt+2: Abrir Recorder</li>
            <li>Escribir nota [nota a grabar] (nota rapida)</li>
            <li>Escribir nota -e (nota extendida)</li>
            <li>Escribir evento -d [fecha] -n [nombre] [descripcion] (evento rapido)</li>
            <li>Escribir evento -e (evento extendido)</li>
            <li>Escribir tarea -t [hora_recordatorio] [descripcion] (tarea rapida)</li>
            <li>Escribir tarea -e (tarea extendida)</li>
            </ul>
          </h6>
          </div>: null }
    </div>
  );
}

export default App;
