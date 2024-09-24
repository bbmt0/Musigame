import React, { useState } from "react";
import InputTextBox from "../../../components/InputTextBox";
import MusicDisplayerGrid from "../../../components/MusicDisplayerGrid";
import axios from "axios";
import styles from "../../../assets/styles/styles";

const TopGuessPlayerScreen = () => {
  const [artistSearch, setArtistSearch] = useState("");
  const [artistsData, setArtistsData] = useState([]);


  const handleArtistSearchChange = (event) => {
	setArtistSearch(event.target.value);
	axios
	  .get(`${process.env.REACT_APP_BFF_URL}/api/v1/artists/${event.target.value}`)
	  .then((response) => {
		setArtistsData(response.data);
        console.log("artistsData", artistsData);

	  })
	  .catch((error) => {
		console.error(error);
	  });
  };

  return (
	<div style={styles.container}>
	  <InputTextBox
		label="Chercher un artiste"
		placeholder="Chercher un artiste"
		value={artistSearch}
		onChange={handleArtistSearchChange}
	  />
	  <MusicDisplayerGrid
		type="artist"
		data={artistsData}
		onSelect={(artist) => console.log("Selected artist:", artist)}
	  />
	</div>
  );
};

export default TopGuessPlayerScreen;