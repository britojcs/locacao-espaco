<template>
  <v-tooltip bottom>
    <template v-slot:activator="{ on, attrs }">
      <v-btn :color="color" v-bind="attrs" v-on="on" @click="raiseClickEvent()">
        <v-icon :color="iconcolor"> {{ icon }} </v-icon>
        {{ text }}
      </v-btn>
    </template>
    <span> {{ tooltip }} </span>
  </v-tooltip>
</template>

<script>
export default {
  name: "ButtonTip",
  props: {
    text: { type: String, required: false, default: "" },
    icon: { type: String, required: false, default: "" },
    color: { type: String, required: false, default: "primary" },
    iconcolor: { type: String, required: false, default: "white" },
    tooltip: { type: String, required: false, default: "" },
    eventname: { type: String, required: false, default: "clickyclicky" },
  },
  methods: {
    raiseClickEvent() {
      this.$emit(this.eventname);
    },
  },
};
</script>