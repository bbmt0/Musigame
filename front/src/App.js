import logo from './logo.svg';
import './App.css';
import HomeScreen from './pages/homescreen/HomeScreen';
import { WelcomeScreen } from './pages/welcomescreen/WelcomeScreen';
import GameCreationScreen from './pages/gamecreationscreen/GameCreationScreen'
import ShareLinkScreen from './pages/sharelinkscreen/ShareLinkScreen';
import JoinGameScreen from './pages/joingamescreen/JoinGameScreen';

function App() {
  return (
    <GameCreationScreen/>
    );
}

export default App;
