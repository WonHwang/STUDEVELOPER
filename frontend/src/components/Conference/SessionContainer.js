import styled from "vue-styled-components"

const number = {
    number: Number
};
  // 비디오 화면 style-components
  const SessionContainer = styled('div', number)`
  display:flex;
  flex-flow:row wrap;
  justify-content: center;
  align-items: center;
  gap: 5px;

  .flexItem{
    align-self: stretch;
  }
  if(${number} > 8){
    .flexItem{
      flex: 0 1 240px;
    }
  }else if(${number} > 4}){
    .flexItem{
      flex: 0 1 480px;
    }
  }else{
    .flexItem{
      flex:0 1 600px;
    }
  }
  `
export default SessionContainer;