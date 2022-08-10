import styled from "styled-components";

export const HTML = styled.div`
  width: 300px;
  height: 100%;
  background-color: #23272a;
`;

export const Header = styled.div`
  width: 100%;
  height: 70px;
  background-color: #4f545c;
  /* background-color: white; */
  border-top-left-radius: 15px;
  border-bottom: 1px solid rgba(0, 0, 0, 0.5);
  padding: 10px;
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
`;

export const Body = styled.div`
  width: 100%;
  height: calc(100% - 70px);
  display: flex;
  flex-direction: column;
  background-color: #4f545c;
  padding: 10px;
`;

export const InnerText = styled.input`
  width: 100%;
  height: 30px;
  border-radius: 8px;
  background-color: #23272a;
  border: none;
  padding-left: 10px;
  color: white;
  font-weight: 600;
`;
