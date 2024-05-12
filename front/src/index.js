import React from "react";
import ReactDOM from "react-dom/client";
import { RouterProvider, createBrowserRouter } from "react-router-dom";
import "./index.css";
import { WelcomeScreen } from "./pages/welcomescreen/WelcomeScreen";
import { GameHolderScreen } from "./pages/gamescreens/GameHolderScreen";
import reportWebVitals from "./reportWebVitals";
import GameCreationScreen from "./pages/gamecreationscreen/GameCreationScreen";
import JoinGameScreen from "./pages/joingamescreen/JoinGameScreen";

const router = createBrowserRouter([
  { path: "/", element: <WelcomeScreen /> },
  { path: "/game", element: <GameHolderScreen /> },
  { path: "/waiting", element: <GameCreationScreen /> },
  { path: "/join", element: <JoinGameScreen /> },
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
