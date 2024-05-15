export const handleErrorMsg = (error, setErrorMessage) => {
    setErrorMessage(error.response.data);
    setTimeout(() => {
        setErrorMessage("");
      }, 4000);
}