<template>
  <div>
    <Navbar />
    <v-container
      pt-2
      :class="{
        singleAppbar: $vuetify.breakpoint.smAndDown,
        doubleAppbar: $vuetify.breakpoint.mdAndUp,
      }"
    >
      <v-main class="pt-1 pl-1">
        <router-view></router-view>
      </v-main>
    </v-container>
  </div>
</template>

<script>
import { mapState } from "vuex";
import Navbar from "@/components/NavbarApp";

export default {
  components: {
    Navbar,
  },
  computed: {
    ...mapState({
      theme: (state) => (state.account.user ? state.account.user.theme : ""),
    }),
  },
  watch: {
    // theme(newTheme, oldTheme) {
    theme(newTheme) {
      this.$vuetify.theme.dark = newTheme === "dark";
    },
  },
  data: () => ({
    //
  }),
};
</script>

<style>
/* scroll bar style */
::-webkit-scrollbar-track {
  --webkit-box-shadow: inset 0 0 6px rgba(0, 0, 0, 0.3);
  background-color: #f5f5f5;
}
::-webkit-scrollbar {
  width: 10px;
  height: 8px;
}
::-webkit-scrollbar-thumb {
  background-color: #757575;
}
.form-label label[for] {
  height: 20px;
  font-size: 10pt;
}
.tableHeader {
  height: 20px;
  font-size: 16px !important;
}
.singleAppbar {
  margin-top: 48px;
}
.doubleAppbar {
  margin-top: 92px;
}
.v-select__slot {
  font-size: smaller;
}
.v-text-field__slot {
  font-size: smaller;
}
input {
  text-transform: uppercase;
}
</style>