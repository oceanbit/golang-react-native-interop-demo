import React, {useEffect} from 'react';
import {
  NativeModules,
  SafeAreaView,
  ScrollView,
  StatusBar,
  Text,
} from 'react-native';

const App = () => {
  const [msg, setMsg] = React.useState('');
  useEffect(() => {
    NativeModules.HelloModule.sayHello().then(phrase => setMsg(phrase));
  });

  return (
    <SafeAreaView>
      <StatusBar />
      <ScrollView contentInsetAdjustmentBehavior="automatic">
        <Text>{msg} and React Native</Text>
      </ScrollView>
    </SafeAreaView>
  );
};

export default App;
