<template>
  <v-tooltip bottom>
    <template v-slot:activator="{ on }">
      <v-icon
        small
        v-on="on"
        :color="color"
        @click="raiseClickEvent()"
        class="pa-1"
      >
        {{ icon }}
      </v-icon>
    </template>
    <span> {{ tooltip }} </span>
  </v-tooltip>
</template>

<script>
export default {
  name: "IconTip",
  props: {
    icon: { type: String, required: false, default: "" },
    color: { type: String, required: false, default: "primary" },
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