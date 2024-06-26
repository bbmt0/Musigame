import React from "react";
import ReactDOM from "react-dom/client";
import { RouterProvider, createBrowserRouter } from "react-router-dom";
import "./index.css";
import GameCreationScreen from "./pages/gamecreationscreen/GameCreationScreen";
import { GameHolderScreen } from "./pages/gamescreens/GameHolderScreen";
import {EndGameScreen} from "./pages/gamescreens/endscreens/EndGameScreen";
import JoinGameScreen from "./pages/joingamescreen/JoinGameScreen";
import { WelcomeScreen } from "./pages/welcomescreen/WelcomeScreen";
import reportWebVitals from "./reportWebVitals";

const router = createBrowserRouter([
  { path: "/", element: <WelcomeScreen /> },
  { path: "/game", element: <GameHolderScreen /> },
  { path: "/waiting", element: <GameCreationScreen /> },
  { path: "/join", element: <JoinGameScreen /> },
  { path: "/game", element: <GameHolderScreen /> },
  { path: "/end-game", element: <EndGameScreen /> }
]);
const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(
  <React.StrictMode>
    <RouterProvider router={router} />
  </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
